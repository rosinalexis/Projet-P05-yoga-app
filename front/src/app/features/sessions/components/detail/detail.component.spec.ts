import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {of} from 'rxjs';

import {DetailComponent} from './detail.component';
import {SessionApiService} from '../../services/session-api.service';
import {TeacherService} from 'src/app/services/teacher.service';
import {SessionService} from '../../../../services/session.service';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionApiServiceMock: Partial<SessionApiService>;
  let sessionServiceMock: Partial<SessionService>;

  const session = {
    users: [],
    name: '',
    description: '',
    date: new Date(),
    createdAt: new Date(),
    updatedAt: new Date(),
    teacher_id: 0,
  };

  sessionApiServiceMock = {
    detail: jest.fn(() => of(session)),
    delete: jest.fn(() => of(null)),
    participate: jest.fn(() => of()),
    unParticipate: jest.fn(() => of()),
  };

  sessionServiceMock = {
    sessionInformation: {id: 69} as any,
    isLogged: true,
    $isLogged: () => of(true),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        MatSnackBarModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
      declarations: [DetailComponent],
      providers: [
        {provide: SessionApiService, useValue: sessionApiServiceMock},
        {provide: TeacherService, useValue: {}},
        {provide: SessionService, useValue: sessionServiceMock},
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate back when clicking the back button', () => {
    const backSpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(backSpy).toHaveBeenCalled();
  });

  it('should participate in the session', () => {
    const sessionId = 'session-id';
    const userId = 'user-id';
    const participateSpy = jest.spyOn(sessionApiServiceMock, 'participate');
    component.sessionId = sessionId;
    component.userId = userId;
    component.participate();
    expect(participateSpy).toHaveBeenCalledWith(sessionId, userId);
  });

  it('should toggle participation when un-participating', () => {
    const sessionId = 'session-id';
    const userId = 'user-id';
    const unParticipateSpy = jest.spyOn(sessionApiServiceMock, 'unParticipate');
    component.sessionId = sessionId;
    component.userId = userId;
    component.unParticipate();
    expect(unParticipateSpy).toHaveBeenCalledWith(sessionId, userId);
  });

  it('should delete the session and show a snackbar', () => {
    const sessionId = 'session-id';
    const snackBarOpenSpy = jest.spyOn(component['matSnackBar'], 'open');
    const deleteSpy = jest.spyOn(sessionApiServiceMock, 'delete');
    component.sessionId = sessionId;
    component.delete();
    expect(deleteSpy).toHaveBeenCalledWith(sessionId);
    expect(snackBarOpenSpy).toHaveBeenCalledWith('Session deleted !', 'Close', {duration: 3000});
  });
});
