package krzysztof.pecyna.eventsViewer.performance.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionFactory;
import krzysztof.pecyna.eventsViewer.performance.controller.api.PerformanceController;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformanceResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformancesResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.PatchPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.dto.PutPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.extern.java.Log;


import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class PerformanceRestController implements PerformanceController {
    private PerformanceService performanceService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public PerformanceRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setPerformanceService(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }


    @Override
    public GetPerformancesResponse getArtistPerformances(UUID id) {
        return performanceService.findAllByArtist(id)
                .map(factory.performancesToResponse())
                .orElseThrow(() -> new NotFoundException("Artist not found"));
    }

    @RolesAllowed(UserRoles.ADMIN)
    @Override
    public GetPerformancesResponse getLocationPerformances(UUID id) {
        return performanceService.findAllByLocation(id)
                .map(factory.performancesToResponse())
                .orElseThrow(() -> new NotFoundException("Location not found"));
    }

    @RolesAllowed(UserRoles.USER)
    @Override
    public GetPerformanceResponse getLocationPerformance(UUID locationId, UUID performanceId) {
        try {
            return performanceService.findForCallerPrincipal(locationId, performanceId)
                    .map(factory.performanceToResponse())
                    .orElseThrow(() -> new NotFoundException("Performance not found in the specified location"));
        } catch (EJBAccessException e) {
            throw new ForbiddenException("Forbidden access!");
        }
    }

    @Override
    public void putLocationPerformance(UUID locationId, UUID performanceId, PutPerformanceRequest request) {
        try {
            request.setLocation(locationId);
            performanceService.createForCallerPrincipal(factory.requestToPerformance().apply(performanceId, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(PerformanceController.class, "getPerformance")
                    .build(performanceId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Performance already exists, to update performance use PATCH method");
            }
            throw ex;
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new BadRequestException(ex.getCause());
            }
        }
    }

    @Override
    public void patchLocationPerformance(UUID locationId, UUID performanceId, PatchPerformanceRequest request) {
        try {
            performanceService.findByLocationAndPerformance(locationId, performanceId).ifPresentOrElse(
                    entity -> performanceService.update(factory.updatePerformance().apply(entity, request), locationId),
                    () -> {
                        throw new NotFoundException("Performance not found in the specified location");
                    });
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new BadRequestException(ex.getCause());
            }
        }
    }

    @Override
    public void patchPerformance(UUID id, PatchPerformanceRequest request) {
        try {
            performanceService.find(id).ifPresentOrElse(
                    entity -> {
                        performanceService.update(factory.updatePerformance().apply(entity, request));
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
        }  catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new BadRequestException(ex.getCause());
            }
        }
    }


    @Override
    public void deleteLocationPerformance(UUID locationId, UUID performanceId) {
        performanceService.findByLocationAndPerformance(locationId, performanceId).ifPresentOrElse(
                entity -> performanceService.delete(performanceId),
                () -> {
                    throw new NotFoundException("Performance not found in the specified location");
                });
    }

    @RolesAllowed(UserRoles.ADMIN)
    @Override
    public GetPerformancesResponse getPerformances() {
        return factory.performancesToResponse().apply(performanceService.findAllForCallerPrincipal());
    }

    @Override
    public GetPerformanceResponse getPerformance(UUID id) {
        return performanceService.find(id)
                .map(factory.performanceToResponse())
                .orElseThrow(() -> new NotFoundException("Performance not found"));
    }
}
