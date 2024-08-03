import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {TeacherService} from './teacher.service';
import {Teacher} from '../interfaces/teacher.interface';
import {HttpErrorResponse} from '@angular/common/http';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpTestingController: HttpTestingController;

  const mockTeachers: Teacher[] = [
    {
      id: 1,
      lastName: 'Super',
      firstName: 'Man',
      createdAt: new Date('2024-07-26T12:55:53'),
      updatedAt: new Date('2024-07-26T12:55:53'),
    },
    {
      id: 2,
      lastName: 'Super',
      firstName: 'Mario',
      createdAt: new Date('2024-07-26T12:55:53'),
      updatedAt: new Date('2024-07-26T12:55:53'),
    },
  ];

  const mockTeacher: Teacher = {
    id: 1,
    lastName: 'Moto',
    firstName: 'John',
    createdAt: new Date('2024-07-26T12:55:53'),
    updatedAt: new Date('2024-07-26T12:55:53'),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService],
    });
    service = TestBed.inject(TeacherService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('all', () => {
    it('should return a list of teachers', () => {
      service.all().subscribe(teachers => {
        expect(teachers).toBeTruthy();
        expect(teachers.length).toBe(2);
        expect(teachers).toEqual(mockTeachers);
      });

      const req = httpTestingController.expectOne('api/teacher');
      expect(req.request.method).toBe('GET');
      req.flush(mockTeachers);
    });

    it('should handle errors if retrieving teachers fails', () => {
      service.all().subscribe({
        next: () => fail('The all() operation should have failed'),
        error: (error: HttpErrorResponse) => {
          expect(error.status).toBe(500);
          expect(error.statusText).toBe('Server Error');
        }
      });

      const req = httpTestingController.expectOne('api/teacher');
      expect(req.request.method).toBe('GET');
      req.flush('Error retrieving teachers', {status: 500, statusText: 'Server Error'});
    });
  });

  describe('detail', () => {
    it('should return a teacher by ID', () => {
      const teacherId = '1';
      service.detail(teacherId).subscribe(teacher => {
        expect(teacher).toBeTruthy();
        expect(teacher).toEqual(mockTeacher);
      });

      const req = httpTestingController.expectOne(`api/teacher/${teacherId}`);
      expect(req.request.method).toBe('GET');
      req.flush(mockTeacher);
    });

    it('should handle errors if retrieving a teacher fails', () => {
      const teacherId = '1';
      service.detail(teacherId).subscribe({
        next: () => fail('The detail() operation should have failed'),
        error: (error: HttpErrorResponse) => {
          expect(error.status).toBe(404);
          expect(error.statusText).toBe('Not Found');
        }
      });

      const req = httpTestingController.expectOne(`api/teacher/${teacherId}`);
      expect(req.request.method).toBe('GET');
      req.flush('Teacher not found', {status: 404, statusText: 'Not Found'});
    });
  });
});
