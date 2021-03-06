/*
 * Copyright (C) 2016 Arthur Gregorio, AG.Software
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
package br.com.webbudget.domain.repositories.journal;

import br.com.webbudget.domain.entities.journal.Entry;
import br.com.webbudget.domain.entities.registration.Vehicle;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 2.0.0
 * @since 2.3.0, 05/06/2016
 */
@Repository
public interface IEntryRepository extends EntityRepository<Entry, Long> {

    /**
     * 
     * @param code
     * @return 
     */
    public Entry findByMovementCode(String code);
    
    /**
     *
     * @param vehicle
     * @return
     */
    public List<Entry> listByVehicle(Vehicle vehicle);

    /**
     * 
     * @param vehicle
     * @param filter
     * @return 
     */
    public List<Entry> listByVehicleAndFilter(Vehicle vehicle, String filter);
}
