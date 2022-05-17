import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './home/book-list/book-list.component';
import { BorrowBookComponent } from './home/borrow-book/borrow-book.component';
import { CardRegistrationComponent } from './home/card-registration/card-registration.component';
import { ContactComponent } from './home/contact/contact.component';
import { Error404Component } from './home/error404/error404.component';
import { HomeComponent } from './home/home.component';
import { IndexComponent } from './home/index/index.component';
import { SigninComponent } from './home/signin/signin.component';
import { SignupComponent } from './home/signup/signup.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'sign-in', component: SigninComponent },
  { path: 'sign-up', component: SignupComponent },
  { path: 'book-list', component: BookListComponent },
  { path: 'borrow-book', component: BorrowBookComponent },
  { path: 'card-registration', component: CardRegistrationComponent },
  { path: 'contact', component: ContactComponent },
  { path: '**', component: Error404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
