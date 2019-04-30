import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';

@Component({
    selector: 'jhi-incident-update',
    templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
    incident: IIncident;
    isSaving: boolean;
    dateDp: any;

    constructor(protected incidentService: IncidentService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incident }) => {
            this.incident = incident;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.incident.id !== undefined) {
            this.subscribeToSaveResponse(this.incidentService.update(this.incident));
        } else {
            this.subscribeToSaveResponse(this.incidentService.create(this.incident));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>) {
        result.subscribe((res: HttpResponse<IIncident>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
