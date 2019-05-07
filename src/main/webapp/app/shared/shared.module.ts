import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BlacklisthotelSharedLibsModule, BlacklisthotelSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BlacklisthotelSharedLibsModule, BlacklisthotelSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BlacklisthotelSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlacklisthotelSharedModule {
  static forRoot() {
    return {
      ngModule: BlacklisthotelSharedModule
    };
  }
}
