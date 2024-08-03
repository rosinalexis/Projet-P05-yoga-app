import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {UserService} from './user.service';
import {HttpErrorResponse} from '@angular/common/http';

describe('UserService', () => {
  let userService: UserService;
  let httpTestingController: HttpTestingController;

  const mockUser = {
    id: 1,
    email: "toto1@toto.com",
    lastName: "toto",
    firstName: "toto",
    admin: true,
    createdAt: new Date("2024-07-26T12:55:53"),
    updatedAt: new Date("2024-07-26T12:55:53"),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService],
    });
    userService = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('getById', () => {
    it('should return a user by ID', () => {
      const userId = '1';
      userService.getById(userId).subscribe(user => {
        expect(user).toBeTruthy();
        expect(user).toEqual(mockUser);
      });

      const req = httpTestingController.expectOne(`api/user/${userId}`);
      expect(req.request.method).toBe('GET');
      req.flush(mockUser);
    });

    it('should handle errors if user retrieval fails', () => {
      const userId = '1';
      userService.getById(userId).subscribe({
        next: () => fail('The getById operation should have failed'),
        error: (error: HttpErrorResponse) => {
          expect(error.status).toBe(404);
          expect(error.statusText).toBe('User not found');
        }
      });

      const req = httpTestingController.expectOne(`api/user/${userId}`);
      expect(req.request.method).toBe('GET');
      req.flush('User not found', {status: 404, statusText: 'User not found'});
    });
  });

  describe('delete', () => {
    it('should delete a user by ID', () => {
      const userId = '1';
      userService.delete(userId).subscribe(response => {
        expect(response).toBeNull();
      });

      const req = httpTestingController.expectOne(`api/user/${userId}`);
      expect(req.request.method).toBe('DELETE');
      req.flush(null);
    });

    it('should handle errors if delete operation fails', () => {
      const userId = '1';
      userService.delete(userId).subscribe({
        next: () => fail('The delete operation should have failed'),
        error: (error: HttpErrorResponse) => {
          expect(error.status).toBe(404);
          expect(error.statusText).toBe('User not found');
        }
      });

      const req = httpTestingController.expectOne(`api/user/${userId}`);
      expect(req.request.method).toBe('DELETE');
      req.flush('User not found', {status: 404, statusText: 'User not found'});
    });
  });
});
