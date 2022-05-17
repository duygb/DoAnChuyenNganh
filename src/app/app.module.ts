import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './home/signup/signup.component';
import { SigninComponent } from './home/signin/signin.component';
import { Error404Component } from './home/error404/error404.component';
import { IndexComponent } from './home/index/index.component';
import { BorrowBookComponent } from './home/borrow-book/borrow-book.component';
import { CardRegistrationComponent } from './home/card-registration/card-registration.component';
import { BookListComponent } from './home/book-list/book-list.component';
import { DetailComponent } from './home/detail/detail.component';
import { ContactComponent } from './home/contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    SignupComponent,
    SigninComponent,
    Error404Component,
    IndexComponent,
    BorrowBookComponent,
    CardRegistrationComponent,
    BookListComponent,
    DetailComponent,
    ContactComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
