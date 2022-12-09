package org.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

@Route
public class MainView extends VerticalLayout {

    public MainView(IpApiService service) {
        add(new H3("See what the interwebs knows about your IP!"));

        TextField ipOrDomain = new TextField("IP or domain name");
        String remoteAddr = VaadinRequest.getCurrent().getRemoteAddr();
        // use either a sane client address or the server address
        if (remoteAddr.startsWith("0") || remoteAddr.startsWith("127")) {
            // most likely testing on local environment, get outbound ip
            try {
                ipOrDomain.setValue(
                        IOUtils.toString(new URL("http://checkip.amazonaws.com/"), Charset.defaultCharset())
                                .trim()
                );
            } catch (IOException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ipOrDomain.setValue(remoteAddr);
        }

        LMap map = new LMap();
        map.setSizeFull();
        map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
        map.setCenter(new LCenter(0,0,1));

        Button button = new Button("Detect!", event -> {
            LocationDTO result = service.findIpDetails(ipOrDomain.getValue());

            map.addLComponents(new LMarker(result.getLat(), result.getLon()));
            map.setCenter(new LCenter(result.getLat(), result.getLon(), 10));
            
            Notification.show(String.format("You appear to be in %s, and your internet "
                    + "provider is  %s. Estimated location plotted on the map. "
                    + "Scary, huh?", result.getCity(), result.getIsp()),
                     3000, Notification.Position.MIDDLE);

        });
        button.addClickShortcut(Key.ENTER);

        HorizontalLayout inputRow = new HorizontalLayout(ipOrDomain, button);
        inputRow.setAlignItems(Alignment.END);
        add(inputRow);

        setSizeFull();
        addAndExpand(map);
    }
}
