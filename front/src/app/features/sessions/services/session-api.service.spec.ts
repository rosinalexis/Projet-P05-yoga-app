import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {SessionApiService} from './session-api.service';
import {Session} from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpTestingController: HttpTestingController;

  const mockSession: Session = {
    id: 1,
    name: 'Test Session',
    description: 'Test Description',
    date: new Date(),
    teacher_id: 1,
    users: [1, 2],
    createdAt: new Date(),
    updatedAt: new Date()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });

    service = TestBed.inject(SessionApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all sessions', () => {
    service.all().subscribe(sessions => {
      expect(sessions).toEqual([mockSession]);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush([mockSession]);
  });

  it('should retrieve a session by id', () => {
    service.detail('1').subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });

  it('should create a new session', () => {
    const newSession: Session = {...mockSession, id: 2};

    service.create(newSession).subscribe(session => {
      expect(session).toEqual(newSession);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newSession);
    req.flush(newSession);
  });

  it('should update a session by id', () => {
    const updatedSession: Session = {...mockSession, name: 'Updated Name'};

    service.update('1', updatedSession).subscribe(session => {
      expect(session).toEqual(updatedSession);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedSession);
    req.flush(updatedSession);
  });

  it('should delete a session by id', () => {
    service.delete('1').subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should participate in a session', () => {
    service.participate('1', '1').subscribe(() => {
    });

    const req = httpTestingController.expectOne('api/session/1/participate/1');
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should unparticipate from a session', () => {
    service.unParticipate('1', '1').subscribe(() => {
    });

    const req = httpTestingController.expectOne('api/session/1/participate/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
