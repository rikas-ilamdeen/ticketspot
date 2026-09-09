import { TestBed } from '@angular/core/testing';

import { VendorService } from '../vendor/vendor.service';
import { provideHttpClient } from '@angular/common/http';

describe('VendorService', () => {
  let service: VendorService;

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [provideHttpClient()] });
    service = TestBed.inject(VendorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
