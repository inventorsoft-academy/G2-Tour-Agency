import {Tour} from './tour';
import {User} from './user';

export class Booking {
  id: number;
  tour: Tour;
  user: User;
  status: boolean;
}
