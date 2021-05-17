import { IngredientModel } from './ingredient-model';
import { InstructionModel } from './instruction-model';
import { TagModel } from './tag-model';
import { CuisineModel } from '../../../cuisines/shared/models/cuisine-model';

export class RecipePayload {
    recipeName: string;
    cookingTime: number;
    yield: number;
    categoryName: string;
    announce: string;
    ingredients: Array<IngredientModel>;
    instructions: Array<InstructionModel>;
    tags: Array<TagModel>;
    cuisines: Array<CuisineModel>;
}