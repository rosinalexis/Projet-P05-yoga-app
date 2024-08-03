import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AuthService} from './auth.service';
import {RegisterRequest} from '../interfaces/registerRequest.interface';
import {LoginRequest} from '../interfaces/loginRequest.interface';
import {SessionInformation} from "../../../interfaces/sessionInformation.interface";


describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  const mockSessionInformation: SessionInformation = {
    token: 'mockToken',
    type: 'Bearer',
    id: 1,
    username: 'alexbiron',
    firstName: 'biron',
    lastName: 'alex',
    admin: false
  };

  const mockRegisterRequest: RegisterRequest = {
    email: 'test@test.com',
    password: 'password123',
    firstName: 'Biron',
    lastName: 'Alex'
  };

  const mockLoginRequest: LoginRequest = {
    email: 'test@test.com',
    password: 'password123'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('register', () => {
    it('should send a POST request to register and return void', () => {
      service.register(mockRegisterRequest).subscribe(response => {
        expect(response).toBeUndefined();
      });

      const req = httpMock.expectOne('api/auth/register');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(mockRegisterRequest);

      req.flush(null);
    });

    it('should handle error during registration', () => {
      service.register(mockRegisterRequest).subscribe({
        next: () => fail('The request should have failed'),
        error: (error) => {
          expect(error.status).toBe(400);
          expect(error.statusText).toBe('Bad Request');
        }
      });

      const req = httpMock.expectOne('api/auth/register');
      req.flush('Registration failed', {status: 400, statusText: 'Bad Request'});
    });
  });

  describe('login', () => {
    it('should send a POST request to login and return session information', () => {
      service.login(mockLoginRequest).subscribe(session => {
        expect(session).toEqual(mockSessionInformation);
      });

      const req = httpMock.expectOne('api/auth/login');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(mockLoginRequest);

      req.flush(mockSessionInformation);
    });

    it('should handle error during login', () => {
      service.login(mockLoginRequest).subscribe({
        next: () => fail('The request should have failed'),
        error: (error) => {
          expect(error.status).toBe(401);
          expect(error.statusText).toBe('Unauthorized');
        }
      });

      const req = httpMock.expectOne('api/auth/login');
      req.flush('Login failed', {status: 401, statusText: 'Unauthorized'});
    });
  });
});
