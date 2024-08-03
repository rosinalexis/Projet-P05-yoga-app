import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterTestingModule} from '@angular/router/testing';
import {LoginComponent} from './login.component';
import {of, throwError} from 'rxjs';
import {SessionInformation} from 'src/app/interfaces/sessionInformation.interface';
import {LoginRequest} from '../../interfaces/loginRequest.interface';
import {AuthService} from "../../services/auth.service";
import {SessionService} from "../../../../services/session.service";
import {HttpClientModule} from "@angular/common/http";
import {Router} from "@angular/router";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  const mockRouter = {
    navigate: jest.fn(),
  };

  const mockAuthService = {
    login: jest.fn(),
  };

  const mockSessionService = {
    logIn: jest.fn(),
    logout: jest.fn(),
  };

  const mockSnackBar = {
    logIn: jest.fn(),
    logout: jest.fn(),
  };

  const mockSessionInformation: SessionInformation = {
    token: 'mockToken',
    type: 'Bearer',
    id: 1,
    username: 'alexbiron',
    firstName: 'alex',
    lastName: 'biron',
    admin: false
  };

  const mockLoginRequest: LoginRequest = {
    email: 'test@test.com',
    password: 'password123'
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ],
      providers: [
        {provide: AuthService, useValue: mockAuthService},
        {provide: Router, useValue: mockRouter},
        {provide: SessionService, useValue: mockSessionService}
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should submit form and navigate on successful home page', () => {
    const loginSpy = jest.spyOn(mockAuthService, 'login').mockReturnValue(of(mockSessionInformation));
    const logInSpy = jest.spyOn(mockSessionService, 'logIn');
    const navigateSpy = jest.spyOn(mockRouter, 'navigate');

    component.form.setValue(mockLoginRequest);
    component.submit();

    expect(loginSpy).toHaveBeenCalledWith(mockLoginRequest);
    expect(logInSpy).toHaveBeenCalledWith(mockSessionInformation);
    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
    expect(component.onError).toBe(false);
  });

  it('should set onError to true on login failure', () => {
    const loginSpy = jest.spyOn(mockAuthService, 'login').mockReturnValue(throwError(() => new Error('Login failed')));

    component.form.setValue(mockLoginRequest);
    component.submit();

    expect(loginSpy).toHaveBeenCalledWith(mockLoginRequest);
    expect(component.onError).toBe(true);
  });

});
