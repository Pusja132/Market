package ru.bandurin.marketplace.domain.specs;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.User_;

@UtilityClass
public class UserSpec {
    public static Specification<User> getByEmail(String email) { // TODO возможно это стоит выпилить
        return (root, query, cb) -> cb.equal(root.get(User_.email), email);
    }
}
