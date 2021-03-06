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
package br.com.webbudget.application.controller.logbook;

import static br.com.webbudget.application.components.NavigationManager.PageType.ADD_PAGE;
import static br.com.webbudget.application.components.NavigationManager.PageType.DELETE_PAGE;
import static br.com.webbudget.application.components.NavigationManager.PageType.DETAIL_PAGE;
import static br.com.webbudget.application.components.NavigationManager.PageType.LIST_PAGE;
import static br.com.webbudget.application.components.NavigationManager.PageType.UPDATE_PAGE;
import br.com.webbudget.application.components.ViewState;
import br.com.webbudget.application.components.table.Page;
import br.com.webbudget.application.controller.FormBean;
import br.com.webbudget.domain.entities.journal.FuelType;
import br.com.webbudget.domain.entities.journal.Refueling;
import br.com.webbudget.domain.entities.registration.Vehicle;
import br.com.webbudget.domain.entities.miscellany.FinancialPeriod;
import br.com.webbudget.domain.entities.registration.MovementClass;
import br.com.webbudget.domain.entities.registration.MovementClassType;
import br.com.webbudget.domain.repositories.entries.CostCenterRepository;
import br.com.webbudget.domain.repositories.entries.MovementClassRepository;
import br.com.webbudget.domain.repositories.journal.RefuelingRepository;
import br.com.webbudget.domain.services.FinancialPeriodService;
import br.com.webbudget.domain.services.RefuelingService;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 2.0.0
 * @since 2.3.0, 27/06/2016
 */
@Named
@ViewScoped
public class RefuelingBean extends FormBean<Refueling> {

    @Getter
    @Setter
    private Refueling refueling;

    @Getter
    private List<Vehicle> vehicles;
    @Getter
    private List<Refueling> refuelings;
    @Getter
    private List<FinancialPeriod> openPeriods;
    @Getter
    private List<MovementClass> movementClasses;

    @Inject
    private RefuelingService refuelingService;
    @Inject
    private FinancialPeriodService periodService;
    
    @Inject
    private RefuelingRepository refuelingRepository;
    @Inject
    private MovementClassRepository movementClassRepository;

    /**
     * 
     */
    @Override
    public void initialize() {
        super.initialize();
        this.temporizeHiding(this.getDefaultMessagesComponentId());
    }

    /**
     * 
     * @param id
     * @param viewState 
     */
    @Override
    public void initialize(long id, ViewState viewState) {
        this.viewState = viewState;
        this.refuelingRepository.findOptionalById(id)
                .orElseGet(Refueling::new);
    }
    
    /**
     * 
     */
    @Override
    protected void initializeNavigationManager() {
        this.navigation.addPage(LIST_PAGE, "listRefuelings.xhtml");
        this.navigation.addPage(ADD_PAGE, "formRefueling.xhtml");
        this.navigation.addPage(UPDATE_PAGE, "formRefueling.xhtml");
        this.navigation.addPage(DETAIL_PAGE, "detailRefueling.xhtml");
        this.navigation.addPage(DELETE_PAGE, "detailRefueling.xhtml");
    }
    
    /**
     * FIXME the search for refuelings is not working well... fix this later 
     * before the release of v3.0
     * 
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return 
     */
    @Override
    public Page<Refueling> load(int first, int pageSize, String sortField, SortOrder sortOrder) {
        return this.refuelingRepository.findAllBy(this.filter.getValue(), null, 
                first, pageSize);
    }

    /**
     * 
     */
    @Override
    public void doSave() {
        this.refuelingService.save(this.refueling);
        this.refueling = new Refueling();
        this.addInfo(true, "refueling.saved");
    }

    /**
     * 
     */
    @Override
    public void doUpdate() {
        // No update for refueling, if you saved wrong, delete and try again
    }

    /**
     * 
     * @return 
     */
    @Override
    public String doDelete() {
        this.refuelingService.delete(this.refueling);
        this.addInfoAndKeep("refueling.deleted");
        return this.changeToListing();
    }

    /**
     * When vehicle is selected, show the cost center linked
     */
    public void onVehicleSelect() {
        this.movementClasses = this.movementClassRepository
                .findByMovementClassTypeAndCostCenter(
                        MovementClassType.OUT, this.refueling.getCostCenter());
    }
    
    /**
     * @return the fuel types
     */
    public FuelType[] getFuelTypes() {
        return FuelType.values();
    }
}
