import { TestBed } from '@angular/core/testing';

import { LoginRESTService } from './login-rest.service';

describe('LoginRESTService', () => {
  let service: LoginRESTService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginRESTService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
