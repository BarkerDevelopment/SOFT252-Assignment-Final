package controllers.repository;

import models.repositories.Repository;
import models.users.User;
import models.users.info.Role;

import java.util.ArrayList;
import java.util.EnumMap;

public class UserRepositoryController
    implements I_RepositoryController< User >{
        private EnumMap< Role, Repository> _repositories;

        /**
         * @return the contents of the repository cast to the correct type.
         */
        @Override
        public ArrayList< User > get() {
            ArrayList< User > content = new ArrayList<>();

            /*
             * I would have like to avoided using a quadratic loop, however due to the casting issues I've been facing
             * it was near impossible. To avoid it, I would have used the ArrayList.addAll() function but alas, you can't
             * do it if there are conflicting types.
             *
             * While I'm using a quadratic loop, it only has a time complexity of O(n) as it only cycles through the list once.
             */
            _repositories.forEach(
                    (role, repository) -> repository.get().forEach(
                            user -> content.add( (User) user)
                    )
            );

            return content;
        }

        /**
         * Adds an item to the repository.
         *
         * @param item the item to be added.
         */
        @Override
        public void add(User item) {
            _repositories.get( item.getId().getRole() )
                    .get().add(item);
        }

        /**
         * Add a collection of items to the repository.
         *
         * @param items the collection of items to be added.
         */
        @Override
        public void add(ArrayList< User > items) {
            items.forEach(this::add);
        }

        /**
         * Removes an item from the repository.
         *
         * @param item the item to be removed.
         */
        @Override
        public void remove(User item) {
            _repositories.get( item.getId().getRole() )
                    .get().remove(item);
        }

        /**
         * Removes a collection of items from the repository.
         *
         * @param items the collection of items to be removed.
         */
        @Override
        public void remove(ArrayList< User > items) {
            items.forEach(this::remove);
        }

        /**
         * Clears the repository.
         */
        @Override
        public void clear() {
            _repositories.forEach(
                    (role, repository) -> repository.get().clear()
            );
        }
}
