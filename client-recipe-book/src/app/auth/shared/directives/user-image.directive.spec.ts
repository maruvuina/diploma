import { UserImageDirective } from './user-image.directive';

describe('UserImageDirective', () => {
  it('should create an instance', () => {
    let httpClientMock;

    let domSanitizerMock; 
    
    const directive = new UserImageDirective(httpClientMock, domSanitizerMock);
    expect(directive).toBeTruthy();
  });
});
