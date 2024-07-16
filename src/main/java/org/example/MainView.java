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
import org.apache.commons.io.IOUtils;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.addons.maplibre.Marker;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        // Note this, API key works only for local testing, create your own in maptiler.com
        MapLibre map = new MapLibre("https://api.maptiler.com/maps/streets/style.json?key=qOkO4acoDsrtuvKp9BxP");
        map.setSizeFull();

        Button button = new Button("Detect!", event -> {
            LocationDTO result = service.findIpDetails(ipOrDomain.getValue());

            Marker marker = map.addMarker(result.getLon(), result.getLat());
            map.flyTo(marker.getGeometry(), 10);

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
