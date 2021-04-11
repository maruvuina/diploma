import { RecipeDetails } from '../../../recipes/shared/models/recipe-details';

export class UserModel {
	id: number;
    fullName: string;
    recipeList: Array<RecipeDetails>;
    registrationDate: string;
}