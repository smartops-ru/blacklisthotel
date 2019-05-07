import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IIncident, Incident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';

@Component({
  selector: 'jhi-incident-update',
  templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
  incident: IIncident;
  isSaving: boolean;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    type: [],
    date: [],
    place: [],
    hotel: [],
    personNameHash: [],
    personDocumentHash: [],
    phoneNumberHash: [],
    description: []
  });

  constructor(protected incidentService: IncidentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ incident }) => {
      this.updateForm(incident);
      this.incident = incident;
    });
  }

  updateForm(incident: IIncident) {
    this.editForm.patchValue({
      id: incident.id,
      type: incident.type,
      date: incident.date,
      place: incident.place,
      hotel: incident.hotel,
      personNameHash: incident.personNameHash,
      personDocumentHash: incident.personDocumentHash,
      phoneNumberHash: incident.phoneNumberHash,
      description: incident.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const incident = this.createFromForm();
    if (incident.id !== undefined) {
      this.subscribeToSaveResponse(this.incidentService.update(incident));
    } else {
      this.subscribeToSaveResponse(this.incidentService.create(incident));
    }
  }

  private createFromForm(): IIncident {
    const entity = {
      ...new Incident(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      date: this.editForm.get(['date']).value,
      place: this.editForm.get(['place']).value,
      hotel: this.editForm.get(['hotel']).value,
      personNameHash: this.editForm.get(['personNameHash']).value,
      personDocumentHash: this.editForm.get(['personDocumentHash']).value,
      phoneNumberHash: this.editForm.get(['phoneNumberHash']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
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
