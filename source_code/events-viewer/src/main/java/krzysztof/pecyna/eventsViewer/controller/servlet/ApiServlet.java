package krzysztof.pecyna.eventsViewer.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import krzysztof.pecyna.eventsViewer.artist.controller.api.ArtistController;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;
import krzysztof.pecyna.eventsViewer.component.exception.AvatarDoesNotExistException;
import krzysztof.pecyna.eventsViewer.component.exception.AvatarExistsException;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private ArtistController artistController;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns{
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern ARTIST = Pattern.compile("/artists/(%s)".formatted(UUID.pattern()));

        public static final Pattern ARTIST_AVATAR = Pattern.compile("/artists/(%s)/avatar".formatted(UUID.pattern()));

        public static final Pattern ARTISTS = Pattern.compile("/artists/?");
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init()throws ServletException{
        super.init();
        artistController = (ArtistController) getServletContext().getAttribute("artistController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTISTS.pattern())) {
                response.setContentType("application/json");
                try {
                    response.getWriter().write(jsonb.toJson(artistController.getArtists()));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.ARTIST.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                try {
                    response.getWriter().write(jsonb.toJson(artistController.getArtist(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                response.setContentType("image/png");
                try {
                    byte[] avatar = artistController.getArtistAvatar(uuid);
                    response.setContentLength(avatar.length);
                    response.getOutputStream().write(avatar);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTIST.pattern())) {
                System.out.println("ARTISTS_PUT");
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                try {
                    artistController.putArtist(uuid, jsonb.fromJson(request.getReader(), PutArtistRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "users", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                try {
                    try(InputStream is = request.getPart("avatar").getInputStream()) {
                        artistController.putArtistAvatar(uuid, is);
                    }
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException | AvatarExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                } catch (Exception ex){
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
                }
                return;
            }
        }
        System.out.println(path);
        System.out.println("OUT OF RANGE PUT");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTIST.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                try {
                    artistController.deleteArtist(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                try {
                    artistController.deleteArtistAvatar(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException | AvatarDoesNotExistException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                catch (IllegalStateException | InternalServerErrorException ex){
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
                }

                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTIST.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                artistController.patchArtist(uuid, jsonb.fromJson(request.getReader(), PatchArtistRequest.class));
                return;
            }
            if(path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                try{
                    try(InputStream is = request.getPart("avatar").getInputStream()) {
                        artistController.patchArtistAvatar(uuid, is);
                    }
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }catch (NotFoundException | AvatarDoesNotExistException ex){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }


}
