package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * This class represents the controller responsible for handling user-related endpoints.
 */
@RestController
@RequestMapping(RestResources.USER)
public class UserController {

    private final UserService userService;

    /**
     * Constructor to inject the UserService dependency.
     *
     * @param userService The service responsible for user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to record a user visit.
     *
     * @param request The request body containing visit information.
     * @return ResponseEntity with HTTP status indicating the result of the operation.
     */
    @PostMapping(RestResources.USER_VISIT)
    public ResponseEntity<String> recordVisit(
            @Validated @RequestBody UserVisitRequestModel request) {
        Optional<String> optional = userService.recordVisit(request);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to record a user like.
     *
     * @param request The request body containing like information.
     * @return ResponseEntity with HTTP status indicating the result of the operation.
     */
    @PostMapping(RestResources.USER_LIKE)
    public ResponseEntity<String> recordLike(
            @Validated @RequestBody UserLikeRequestModel request) {
        Optional<String> optional = userService.recordLike(request);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint for testing purposes to create a user.
     *
     * @return A string indicating the success of the user creation.
     */
    @GetMapping("/create")
    public String createUser() {
        userService.createUser();
        return "test";
    }
}
