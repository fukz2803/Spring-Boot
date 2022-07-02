package vn.techmaster.blog.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import vn.techmaster.blog.dto.BlogInfo;
import vn.techmaster.blog.dto.UserDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SqlResultSetMapping(
        name = "listBlogInfo",
        classes = @ConstructorResult(
                targetClass = BlogInfo.class,
                columns = {
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "slug", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "thumbnail", type = String.class),
                        @ColumnResult(name = "pulished_at", type = String.class),
                        @ColumnResult(name = "count_comment", type = Integer.class),
                        @ColumnResult(name = "author", type = String.class),
                }
        )
)
@NamedNativeQuery(
        name = "getAllBlogInfo",
        resultSetMapping = "listBlogInfo",
        query = "SELECT b.id, b.title, b.slug, b.description, b.thumbnail,\n" +
        "DATE_FORMAT(b.description, '%d/%m/%Y') as pulished_at,\n" +
        "json_object('id', u.id, 'name', u.name) as author,\n" +
        "COUNT(c.id) as count_comment\n" +
        "from blog b\n" +
        "left join `user` u on b.user_id = u.id\n" +
        "LEFT JOIN comment c on b.id =c.blog_id\n" +
        "WHERE b.status = 1\n" +
        "GROUP BY b.id\n" +
        "ORDER BY b.pulished_at DESC\n"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(generator = "custom_generate")
    @GenericGenerator(name = "custom_generate", strategy = "vn.techmaster.blog.generator.CustomIdGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "pulished_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime publishedAt;

    @Column(name = "status", columnDefinition = "int default 0")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "blog_categories",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private List<Category> categories = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createAt = LocalDateTime.now().minusMonths(2);
        updatedAt =createAt;
        if(status == 1) {
            publishedAt = updatedAt;
        }
    }
}