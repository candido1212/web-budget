/*
 * Copyright (C) 2018 Arthur Gregorio, AG.Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.webbudget.application.components.table;

import br.com.webbudget.domain.entities.PersistentEntity;
import java.util.List;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * The data provider for better use of lazy datamodel with Primefaces
 *
 * @param <T> the type of data for the provider to provide
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 3.0.0, 20/03/2018
 */
public interface LazyDataProvider<T extends PersistentEntity> {

    /**
     * This is the simple method to retrieve data with lazy loading
     *
     * @param first the start of the pagination
     * @param pageSize the maximum size of the page
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @return the page to be paginated on the {@link LazyDataModel}
     */
    Page<T> load(int first, int pageSize, String sortField, SortOrder sortOrder);
    
    /**
     * Used to retrieve data from databse with multisorting from data component
     *
     * If you don't want to use multisorting, theres no need to implement this
     * method because it already have a default implementation with empty list
     * return
     *
     * @param first the start of the pagination
     * @param pageSize the maximum size of the page
     * @param sortFields the fields to sort
     * @return the page to be paginated on the {@link LazyDataModel}
     */
    default public Page<T> load(int first, int pageSize, List<SortMeta> sortFields) {
        return Page.empty();
    }
}
