import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MatInputModule} from '@angular/material/input';
import {Router} from '@angular/router';
import {of, throwError} from 'rxjs';

import {RegisterComponent} from './register.component';
import {AuthService} from '../../services/auth.service';
import {RegisterRequest} from '../../interfaces/registerRequest.interface';
import {MatCardModule} from "@angular/material/card";
import {ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";


describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;


  const mockRouter = {
    navigate: jest.fn(),
  };

  const mockAuthService = {
    register: jest.fn(),
  };


  const mockRegisterRequest: RegisterRequest = {
    email: 'test@example.com',
    firstName: 'Alex',
    lastName: 'Borin',
    password: 'password'
  };


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        {provide: AuthService, useValue: mockAuthService},
        {provide: Router, useValue: mockRouter},
      ]
    }).compileComponents()
      .then(
        () => {
          fixture = TestBed.createComponent(RegisterComponent);
          component = fixture.componentInstance;
        }
      );

    fixture.detectChanges();
  });


  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should submit form and navigate on successful registration', () => {
    const registerSpy = jest.spyOn(mockAuthService, 'register').mockReturnValue(of(void 0));
    const navigateSpy = jest.spyOn(mockRouter, 'navigate');

    component.form.setValue(mockRegisterRequest);
    component.submit();

    expect(registerSpy).toHaveBeenCalledWith(mockRegisterRequest);
    expect(navigateSpy).toHaveBeenCalledWith(['/login']);
    expect(component.onError).toBe(false);
  });

  it('should set onError to true on registration failure', () => {
    const registerSpy = jest.spyOn(mockAuthService, 'register').mockReturnValue(throwError(() => new Error('Registration failed')));

    component.form.setValue(mockRegisterRequest);
    component.submit();

    expect(registerSpy).toHaveBeenCalledWith(mockRegisterRequest);
    expect(component.onError).toBe(true);
  });

});
