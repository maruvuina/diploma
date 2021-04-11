import { RecipeImageDirective } from './recipe-image.directive';

describe('RecipeImageDirective', () => {
  it('should create an instance', () => {
    let httpClientMock;

    let domSanitizerMock; 

    const directive = new RecipeImageDirective(httpClientMock, domSanitizerMock);
    expect(directive).toBeTruthy();
  });
});
