import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const vendorGuard: CanActivateFn = (_, state) => {
  const router = inject(Router);
  const session = JSON.parse(sessionStorage.getItem('userSession') || '{}');
  if (session.role === 'vendor') {
    return true;
  }

  return router.createUrlTree(['/login'], { queryParams: { returnUrl: state.url } });
};