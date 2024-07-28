package doctorBookingApp.dto;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfileDTO {

    private Long id;

    // Обязательное поле. Имя не может быть пустым и должно содержать не более 40 символов
    @NotBlank(message = "Имя обязательно для заполнения")
    @Size(max = 40, message = "Имя должно содержать не более 40 символов")
    private String firstName;

    // Обязательное поле. Фамилия не может быть пустой и должна содержать не более 50 символов
    @NotBlank(message = "Фамилия обязательна для заполнения")
    @Size(max = 50, message = "Фамилия должна содержать не более 50 символов")
    private String lastName;

    // Обязательное поле. ID департамента не может быть null
    @NotNull(message = "ID департамента обязательно для заполнения")
    private Long departmentId;

    // Обязательное поле. Специализация не может быть пустой и должна содержать не более 255 символов
    @NotBlank(message = "Специализация обязательна для заполнения")
    @Size(max = 255, message = "Специализация должна содержать не более 255 символов")
    private String specialization;

    // Обязательное поле. Опыт работы должен быть положительным числом и не превышать 100 лет
    @NotNull(message = "Годы опыта обязательны для заполнения")
    @Min(value = 0, message = "Годы опыта должны быть положительным числом")
    @Max(value = 100, message = "Годы опыта должны быть не более 100")
    private Integer experienceYears;

    // Необязательное поле. Отзывы
    private Integer reviewId; // Это поле необязательно, поэтому аннотации валидации нет
}