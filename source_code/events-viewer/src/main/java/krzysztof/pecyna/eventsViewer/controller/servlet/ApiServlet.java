package krzysztof.pecyna.eventsViewer.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import krzysztof.pecyna.eventsViewer.artist.controller.api.ArtistController;
import krzysztof.pecyna.eventsViewer.location.controller.api.LocationController;
import krzysztof.pecyna.eventsViewer.performance.controller.api.PerformanceController;
import krzysztof.pecyna.eventsViewer.controller.servlet.exception.NotFoundException;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private final ArtistController artistController;

    private final PerformanceController performanceController;

    private final LocationController locationController;

    private String avatarPath;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern ARTIST = Pattern.compile("/artists/(%s)".formatted(UUID.pattern()));

        public static final Pattern ARTISTS = Pattern.compile("/artists/?");

        public static final Pattern PERFORMANCE = Pattern.compile("/performances/(%s)".formatted(UUID.pattern()));

        public static final Pattern PERFORMANCES = Pattern.compile("/performances/?");

        public static final Pattern LOCATION = Pattern.compile("/locations/(%s)".formatted(UUID.pattern()));

        public static final Pattern LOCATIONS = Pattern.compile("/locations/?");

        public static final Pattern LOCATION_PERFORMANCES = Pattern.compile("/locations/(%s)/performances/?".formatted(UUID.pattern()));

        public static final Pattern ARTIST_PERFORMANCES = Pattern.compile("/artists/(%s)/performances/?".formatted(UUID.pattern()));

        public static final Pattern ARTIST_AVATAR = Pattern.compile("/artists/(%s)/avatar".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(ArtistController artistController, PerformanceController performanceController, LocationController locationController) {
        this.artistController = artistController;
        this.performanceController = performanceController;
        this.locationController = locationController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTISTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(artistController.getArtists()));
                return;
            } else if (path.matches(Patterns.ARTIST.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                try {
                    response.getWriter().write(jsonb.toJson(artistController.getArtist(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.ARTIST_PERFORMANCES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.ARTIST_PERFORMANCES, path);
                try {
                    response.getWriter().write(jsonb.toJson(performanceController.getArtistPerformances(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                response.setContentType("image/png");
                try {
                    byte[] avatar = artistController.getArtistAvatar(uuid, avatarPath);
                    response.setContentLength(avatar.length);
                    response.getOutputStream().write(avatar);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.PERFORMANCES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(performanceController.getPerformances()));
                return;
            } else if (path.matches(Patterns.PERFORMANCE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PERFORMANCE, path);
                try {
                    response.getWriter().write(jsonb.toJson(performanceController.getPerformance(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.LOCATIONS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(locationController.getLocations()));
                return;
            } else if (path.matches(Patterns.LOCATION.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.LOCATION, path);
                try {
                    response.getWriter().write(jsonb.toJson(locationController.getLocation(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.LOCATION_PERFORMANCES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.LOCATION_PERFORMANCES, path);
                try {
                    response.getWriter().write(jsonb.toJson(performanceController.getLocationPerformances(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Get method bad request");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTIST.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                try {
                    artistController.putArtist(uuid, jsonb.fromJson(request.getReader(), PutArtistRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "artists", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                try {
                    artistController.putArtistAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.PERFORMANCE.pattern())) {
                UUID uuid = extractUuid(Patterns.PERFORMANCE, path);
                try {
                    performanceController.putPerformance(uuid, jsonb.fromJson(request.getReader(), PutPerformanceRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "performances", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.LOCATION.pattern())) {
                UUID uuid = extractUuid(Patterns.LOCATION, path);
                try {
                    locationController.putLocation(uuid, jsonb.fromJson(request.getReader(), PutLocationRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "locations", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Put method bad request");
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
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                try {
                    artistController.deleteArtistAvatar(uuid, avatarPath);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.PERFORMANCE.pattern())) {
                UUID uuid = extractUuid(Patterns.PERFORMANCE, path);
                try {
                    performanceController.deletePerformance(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.LOCATION.pattern())) {
                UUID uuid = extractUuid(Patterns.LOCATION, path);
                try {
                    locationController.deleteLocation(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Delete method bad request");
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ARTIST.pattern())) {
                UUID uuid = extractUuid(Patterns.ARTIST, path);
                try {
                    artistController.patchArtist(uuid, jsonb.fromJson(request.getReader(), PatchArtistRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.ARTIST_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.ARTIST_AVATAR, path);
                try {
                    artistController.patchArtistAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.PERFORMANCE.pattern())) {
                UUID uuid = extractUuid(Patterns.PERFORMANCE, path);
                try {
                    performanceController.patchPerformance(uuid, jsonb.fromJson(request.getReader(), PatchPerformanceRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.LOCATION.pattern())) {
                UUID uuid = extractUuid(Patterns.LOCATION, path);
                try {
                    locationController.patchLocation(uuid, jsonb.fromJson(request.getReader(), PatchLocationRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Patch method bad request");
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
