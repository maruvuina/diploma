import { RecipeDetails } from '../../../recipes/shared/models/recipe-details';
import { UserDetails } from '../models/user-details';

export class UserModel {
	id: number;
    fullName: string;
    email: string;
    recipeList: Array<RecipeDetails>;
    registrationDate: string;
    followers: Array<UserDetails>;
    followings: Array<UserDetails>;
}