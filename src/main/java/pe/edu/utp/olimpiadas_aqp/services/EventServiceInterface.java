package pe.edu.utp.olimpiadas_aqp.services;

import pe.edu.utp.olimpiadas_aqp.models.requests.event.EventReq;
import pe.edu.utp.olimpiadas_aqp.models.requests.user.client.ClientReq;
import pe.edu.utp.olimpiadas_aqp.models.responses.event.CreateEventRes;
import pe.edu.utp.olimpiadas_aqp.models.responses.event.GetEventRes;
import pe.edu.utp.olimpiadas_aqp.models.responses.user.client.CreateClientRes;

import java.util.List;

public interface EventServiceInterface {
    List<GetEventRes> getAll();
    CreateEventRes createEvent(EventReq eventReq);
}