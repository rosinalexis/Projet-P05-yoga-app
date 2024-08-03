import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {of} from 'rxjs';
import {MeComponent} from './me.component';
import {UserService} from '../../services/user.service';
import {SessionService} from '../../services/session.service';
import {User} from '../../interfaces/user.interface';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";


describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  const mockUserService = {
    getById: jest.fn().mockReturnValue(
      of({
        id: 100,
        email: 'user@gmail.com',
        firstName: 'user',
        lastName: 'user',
        admin: true,
        password: 'password',
        createdAt: new Date(),
        updatedAt: new Date(),
      } as User)),
    delete: jest.fn().mockReturnValue(of(null)),
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 100,
    },
    logOut: jest.fn(),
  };

  const mockRouter = {
    navigate: jest.fn(),
  };

  const mockMatSnackBar = {
    open: jest.fn(),
  };

  beforeEach(async () => {


    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatInputModule,
        MatSnackBarModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        HttpClientModule,
      ],
      providers: [
        {provide: UserService, useValue: mockUserService},
        {provide: SessionService, useValue: mockSessionService},
        {provide: MatSnackBar, useValue: mockMatSnackBar},
        {provide: Router, useValue: mockRouter},
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load user information', () => {
      component.ngOnInit();

      expect(mockUserService.getById).toHaveBeenCalledWith(mockSessionService.sessionInformation.id.toString());
      expect(component.user).toEqual({
        id: 100,
        email: 'user@gmail.com',
        firstName: 'user',
        lastName: 'user',
        admin: true,
        password: 'password',
        createdAt: expect.any(Date),
        updatedAt: expect.any(Date),
      });
    });

  });

  describe('back', () => {
    it('should call window.history.back', () => {
      const backSpy = jest.spyOn(window.history, 'back');
      component.back();
      expect(backSpy).toHaveBeenCalled();
    });
  });

  describe('delete', () => {
    it('should call userService.delete, show a snackbar and navigate to home', () => {
      component.delete();
      expect(mockUserService.delete).toHaveBeenCalledWith(
        mockSessionService.sessionInformation.id.toString()
      );
      expect(mockSessionService.logOut).toHaveBeenCalled();
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
    });
  });
});
