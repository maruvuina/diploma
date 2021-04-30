import { IngredientModel } from './ingredient-model';
import { InstructionModel } from './instruction-model';
import { CommentModel } from '../../../comment/shared/models/comment-model';
import { TagModel } from '../../shared/models/tag-model';

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
}