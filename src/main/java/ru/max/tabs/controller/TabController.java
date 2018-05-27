package ru.max.tabs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.max.tabs.dto.ErrorItem;
import ru.max.tabs.dto.TabFilter;
import ru.max.tabs.dto.TabItem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/tabs", produces = APPLICATION_JSON_VALUE)
public class TabController {

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getByID(@PathVariable(value = "id") BigInteger id) {

        if (BigInteger.ZERO.equals(id)) {
            return new ResponseEntity<>(new ErrorItem("code", "message"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TabItem("title", "url", id), HttpStatus.OK);
    }


    @RequestMapping(path = "/filter", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getByFilter(@RequestBody TabFilter filter) {

        if (filter == null || (filter.getId() == null && filter.getTitle() == null && filter.getUrl() == null)) {
            return new ResponseEntity<>(new ErrorItem("code", "message"), HttpStatus.NOT_FOUND);
        }

        List<TabItem> tabs = new ArrayList<>();
        tabs.add(new TabItem("title", "www.vermillion.com", BigInteger.ONE));
        tabs.add(new TabItem("title", "www.vermillion.com", BigInteger.TEN));

        return new ResponseEntity<>(tabs, HttpStatus.OK);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateTabById(@PathVariable BigInteger id, @RequestBody TabItem tabItem) {

        if (id.equals(BigInteger.ZERO)) {
            return new ResponseEntity<>(new ErrorItem("code", "message"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(tabItem, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody TabItem tabItem) {
        if (tabItem == null) {
            return new ResponseEntity<>(new ErrorItem("code", "message"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        tabItem.setId(BigInteger.ONE);

        return new ResponseEntity<>(tabItem, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") BigInteger id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
