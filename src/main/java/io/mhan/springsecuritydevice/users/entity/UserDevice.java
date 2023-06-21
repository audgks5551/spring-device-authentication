package io.mhan.springsecuritydevice.users.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@EqualsAndHashCode(of = {"deviceName", "user"})
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"device_id", "user_id"})
})
public class UserDevice {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
