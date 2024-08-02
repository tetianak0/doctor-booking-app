package doctorBookingApp.controller.api;


import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.dto.StandardResponseDto;
import doctorBookingApp.exeption.RestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/api/users")
public interface UserApi {

    @PostMapping("/confirm")
    ResponseEntity<String> confirmationUser(@RequestParam String confirmationCode);


    @Operation(summary = "Получение информации о пользователе по ID") 

 
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })


    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable long userId);
   

    @Operation(summary = "Получение информации о пользователе по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @GetMapping("/by-email/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) throws RestException;


    @Operation(summary = "Получение пользователя по телефону")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @GetMapping("/by-phone/{phoneNumber}")
    ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) throws RestException;


    @Operation(summary = "Обновление информации о пользователе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе обновлена.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @PutMapping("/{userId}")
    ResponseEntity<UserDTO> editUser(@PathVariable Long userID, @RequestBody UserDTO userDto);


    @Operation(summary = "Получение списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей.")
    })

    @GetMapping
    ResponseEntity<List<UserDTO>> findAllUserById();


    @Operation(summary = "Удаление пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален."),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.")

    })

    @GetMapping("/{userID}")
    ResponseEntity<UserDTO> deleteUserById(@PathVariable long userID);



    @Operation(summary = "Удаление пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален."),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @DeleteMapping("/by-email/{email}")
    ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) throws RestException;


    @Operation(summary = "Удаление пользователя по телефону")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален."),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @DeleteMapping("/by-phone/{phoneNumber}")
    ResponseEntity<Void> deleteUserByPhoneNumber(@PathVariable String phoneNumber) throws RestException;




    @Operation(summary = "Получение информации о пользователях по фамилии и имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователи найдены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователи не найдены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/by-name")
    ResponseEntity<List<UserDTO>> getUsersByFullName(@RequestParam String surName, @RequestParam String firstname);





}
