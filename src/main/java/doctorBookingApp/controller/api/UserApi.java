package doctorBookingApp.controller.api;


import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.dto.StandardResponseDto;
import doctorBookingApp.exeption.RestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @GetMapping("/{userID}")
    ResponseEntity<UserDTO> getUserById(@PathVariable long userID);

    @PutMapping("/{userID}")
    ResponseEntity<UserDTO> editUser(@PathVariable Long userID, @RequestBody UserDTO userDto);


    @Operation(summary = "Получение списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей")
    })

    @GetMapping
    ResponseEntity<List<UserDTO>> findAllUserById();


    @Operation(summary = "Удаление пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")

    })

    @GetMapping("/{userID}")
    ResponseEntity<UserDTO> deleteUserById(@PathVariable long userID);


//ПОЛУЧЕНИЕ\УДАЛЕНИЕ ПО ПОЧТЕ И ТЕЛЕФОНУ, ПО ИМЕНИ С КОНТРОЛЕМ??????

}
