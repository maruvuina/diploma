import { IngredientModel } from '../../../ingredients/shared/models/ingredient-model';
import { InstructionModel } from './instruction-model';
import { CommentModel } from '../../../comment/shared/models/comment-model';
import { TagModel } from '../../../tags/shared/models/tag-model';
import { CuisineModel } from '../../../cuisines/shared/models/cuisine-model';

export class RecipeModel {
    idRecipe: number;
    recipeName: string;
    cookingTime: number;
    yield: number;
    createdDate: string;
    authorName: string;
    categoryName: string;
    announce: string;
    ingredients: IngredientModel[];
    instructions: InstructionModel[];
    comments: CommentModel[];
    tags: TagModel[];
    likesCount: number;
    idAuthor: number;
    cuisines: CuisineModel[];
}