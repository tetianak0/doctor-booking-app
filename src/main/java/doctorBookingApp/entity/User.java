package doctorBookingApp.entity;

import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.entity.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 40)
    private String firstname;

    @NotBlank
    @Column(length = 50)
    private String surName;

    @NotBlank
    @Column(length = 10)
    private String birthDate;

    @NotBlank
    @Column(length = 14, unique = true)
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String hashPassword;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private State state;

    /*
Аннотации, такие как @Column и @Enumerated,
обеспечивают управление схемой базы данных и поведением полей.
 */


//    @OneToMany(mappedBy = "user")
//    private Set<ConfirmationCode> codes;

//

    /*
    Переопределение методов equals и hashCode
Методы equals и hashCode переопределены для обеспечения правильного сравнения объектов User.

equals метод:

Проверяет, равны ли два объекта (this и o).
Сначала проверяет, ссылаются ли оба объекта на один и тот же экземпляр.
Затем проверяет, является ли другой объект null.
Определяет эффективный класс объектов, учитывая, что объекты могут быть прокси-классами Hibernate.
Сравнивает идентификаторы (id) двух объектов User.
hashCode метод:

Возвращает хэш-код для объекта.
Если объект является прокси-объектом Hibernate, используется хэш-код фактического класса.
Иначе используется хэш-код текущего класса.
     */
//    @Override
//    public final boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null) return false;
//        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
//        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
//        if (thisEffectiveClass != oEffectiveClass) return false;
//        User user = (User) o;
//        return getId() != null && Objects.equals(getId(), user.getId());
//    }
////
//    @Override
//    public final int hashCode() {
//        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
//    }





}
