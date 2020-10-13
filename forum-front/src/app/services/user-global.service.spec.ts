import { TestBed } from '@angular/core/testing';

import { UserGlobalService } from './user-global.service';

describe('UserGlobalService', () => {
  let service: UserGlobalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserGlobalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
