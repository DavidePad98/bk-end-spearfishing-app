package capstonepj_bkend.bkendcpj.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    @OneToMany(mappedBy = "ticket")
    private List<Post> posts;
}
