import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormComponent} from './form.component';
import {SessionService} from 'src/app/services/session.service';
import {Router} from '@angular/router';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {NgZone} from '@angular/core';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionService: SessionService;
  let router: Router;
  let ngZone: NgZone;

  const mockSessionInformation = {
    admin: true,
    token: 'token',
    id: 1,
    username: 'username',
    firstName: 'firstName',
    lastName: 'lastName',
    type: 'type',
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          {path: 'update', component: FormComponent},
        ]),
        HttpClientTestingModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule
      ],
      declarations: [FormComponent],
      providers: [
        {
          provide: SessionService, useValue: {sessionInformation: mockSessionInformation},
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    ngZone = TestBed.inject(NgZone);
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to sessions page if not admin', () => {
    sessionService.sessionInformation = {
      admin: false,
      token: 'token',
      id: 1,
      username: 'username',
      firstName: 'firstName',
      lastName: 'lastName',
      type: 'type',
    };
    const navigateSpy = jest.spyOn(router, 'navigate');
    ngZone.run(() => {
      component.ngOnInit();
    });
    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
  });

  it('should show validation errors when the form is invalid', () => {
    const formElement: HTMLElement = fixture.nativeElement;
    const submitButton = formElement.querySelector('button[type="submit"]') as HTMLButtonElement;
    component.sessionForm?.markAllAsTouched();
    fixture.detectChanges();
    submitButton.click();
    fixture.detectChanges();
    expect(formElement.querySelectorAll('mat-form-field.ng-invalid')).toHaveLength(4);
  });
});
