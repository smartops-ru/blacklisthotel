import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'incident',
                loadChildren: './incident/incident.module#BlacklisthotelIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#BlacklisthotelIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#BlacklisthotelIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#BlacklisthotelIncidentModule'
            },
            {
                path: 'incident',
                loadChildren: './incident/incident.module#BlacklisthotelIncidentModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlacklisthotelEntityModule {}
