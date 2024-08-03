import {TestBed} from '@angular/core/testing';
import {SessionService} from './session.service';
import {SessionInformation} from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  const mockSession: SessionInformation = {
    token: 'mock-token',
    type: 'Bearer',
    id: 1,
    username: 'marielogo',
    firstName: 'Logo',
    lastName: 'Marie',
    admin: true,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SessionService],
    });
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('logIn', () => {
    it('should set session information and update login status', () => {
      service.logIn(mockSession);

      expect(service.sessionInformation).toEqual(mockSession);
      expect(service.isLogged).toBe(true);
      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBe(true);
      });
    });
  });

  describe('logOut', () => {
    it('should clear session information and update login status', () => {
      service.logIn(mockSession);

      service.logOut();

      expect(service.sessionInformation).toBeUndefined();
      expect(service.isLogged).toBe(false);
      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBe(false);
      });
    });
  });

  describe('$isLogged', () => {
    it('should initially emit false', () => {
      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBe(false);
      });
    });

    it('should emit true after login', () => {
      service.logIn(mockSession);

      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBe(true);
      });
    });

    it('should emit false after logout', () => {
      service.logIn(mockSession);
      service.logOut();

      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBe(false);
      });
    });
  });
});
