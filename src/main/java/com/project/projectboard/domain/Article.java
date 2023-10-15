package com.project.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
    @Id     // 기본키 직접 할당
    @GeneratedValue(strategy = GenerationType.IDENTITY)       // (mysql => IDENTITY)자동으로 데이터 베이스가 생성하는 값을 설정
    private Long id;

    // * 특정 필드에만 Setter 를 설정하는 이유: 특정 필드만 접근이 가능하도록 설정하기 위해.
    // ex) id, metadata(createAt, createBy, modifiedAt, modifiedBy)
    @Setter @Column(nullable = false) private String title;       // 제목
    @Setter @Column(nullable = false, length = 10000) private String content;     // 본문

    @Setter private String hashtag;     // 해시태그 (optional = Nullable)

    // 중복을 허용하지 않고 리스트로 보겠다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // factory method: new keyword 를 사용하지 않고, 객체가 생성되도록
    // article을 생성할 때, Title, content, hashtag 를 필요로 한다는 걸 알려주는 것?!
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // EqualsHashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
