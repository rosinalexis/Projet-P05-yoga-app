import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MatToolbarModule} from '@angular/material/toolbar';
import {RouterTestingModule} from '@angular/router/testing';
import {expect} from '@jest/globals';

import {AppComponent} from './app.component';
import {of} from "rxjs";


describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let component: AppComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    component.$isLogged = () => of(true);
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should display "Yoga app" in the toolbar', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('mat-toolbar span')?.textContent).toContain('Yoga app');
  });

  it('should show logged-in links when $isLogged() returns true', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.link')?.textContent).toContain('Sessions');
    expect(compiled.querySelectorAll('.link')[2].textContent).toContain('Logout');
  });

  it('should show login and register links when $isLogged() returns false', () => {
    component.$isLogged = () => of(false);
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.link')?.textContent).toContain('Login');
    expect(compiled.querySelectorAll('.link')[1].textContent).toContain('Register');
  });

  it('should call logout() when logout link is clicked', () => {
    jest.spyOn(component, 'logout');
    component.$isLogged = () => of(true);
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    const logoutLink = compiled.querySelector('.link:nth-of-type(3)') as HTMLSpanElement;
    logoutLink?.click();

    expect(component.logout).toHaveBeenCalled();
  });

});
