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

/* интерфейс UserApi содержит описание API для работы с пользователями, включая получение информации
о пользователе по ID и обновление данных пользователя.
интерфейс UserApi включает в себя аннотации из OpenAPI/Swagger для автоматической генерации документации API
и  интерактивных интерфейсов для тестирования API.
 */

//   @RequestMapping Указывает, что все методы в этом интерфейсе будут обрабатываться по URL, начинающемуся с /api/users.
//    Это базовый путь для всех методов API в этом интерфейсе.

@RequestMapping("/api/users")
public interface UserApi {

    @PostMapping("/confirm")
    ResponseEntity<String> confirmationUser(@RequestParam String confirmationCode);


    @Operation(summary = "Получение информации о пользователе по ID") //Используется для генерации документации в Swagger/OpenAPI.

    //value: Массив из @ApiResponse аннотаций, описывающих возможные ответы для этого метода API.
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
/*
@ApiResponse

responseCode: HTTP статус-код, который возвращает этот метод.
description: Описание ответа.
content: Описание контента ответа.
mediaType: Тип медиаконтента (например, "application/json").
schema: Схема данных, возвращаемых в этом ответе.
implementation: Класс, который используется для описания схемы данных (например, UserDto или StandardResponseDto).

@Content - Указывает на тип контента и схему данных, возвращаемую в ответе.

@Schema - oписывает модель данных для Swagger/OpenAPI.
implementation: Класс, который используется для описания схемы данных.
 */

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable long userId);
    /*
    @GetMapping("/{userId}") Указывает, что этот метод обрабатывает HTTP GET запросы по URL /api/users/{userId}.
@PathVariable long userId: Указывает, что userId будет извлечен из пути запроса.
     */

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

/*
@PutMapping("/{userId}") Указывает, что этот метод обрабатывает HTTP PUT запросы по URL /api/users/{userId}.

@RequestBody UserDto userDto: Указывает, что тело запроса должно быть преобразовано в объект UserDto.
 */
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



    //ПО ИМЕНИ ????? НАДО ЛИ?????

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
