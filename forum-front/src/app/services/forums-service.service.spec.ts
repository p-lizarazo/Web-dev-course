import { TestBed } from '@angular/core/testing';

import { ForumsServiceService } from './forums-service.service';

describe('ForumsServiceService', () => {
  let service: ForumsServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ForumsServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
