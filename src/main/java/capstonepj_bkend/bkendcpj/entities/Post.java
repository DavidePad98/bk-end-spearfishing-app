package capstonepj_bkend.bkendcpj.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
@JsonIgnoreProperties({"comments", "ticketId"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    private String text;
    private String urlContent;
    private LocalDate postCreationDate;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticketId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Like> likes = new ArrayList<>();
}
