import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForumListComponent } from './forum/forum-list/forum-list.component';
import { TopicListComponent } from './topic/topic-list/topic-list.component';
import { LoginComponent } from './login/login.component';
import { ForumEditComponent } from './forum/forum-edit/forum-edit.component';
import { ForumCreateComponent } from './forum/forum-create/forum-create.component';
import { TopicCreateComponent } from './topic/topic-create/topic-create.component';
import { TopicViewComponent } from './topic/topic-view/topic-view.component';
import { TopicEditComponent } from './topic/topic-edit/topic-edit.component';
import { ModComponent } from './mod/mod/mod.component';

const routes: Routes = [

  { path: '', pathMatch: 'full', redirectTo: 'login'},
  { path: 'login', component: LoginComponent},
  { path: 'forum', component: ForumListComponent},
  { path: 'mod', component: ModComponent},
  { path: 'forum/:id/topics', component: TopicListComponent},
  { path: 'forum/:id/topics/create', component: TopicCreateComponent},
  { path: 'forum/:id/topics/:idTopic', component: TopicViewComponent},
  { path: 'forum/:id/topics/edit/:idTopic', component: TopicEditComponent},
  { path: 'forum/edit/:id', component: ForumEditComponent},
  { path: 'forum/create', component: ForumCreateComponent},
  { path: ':forumId', component: TopicListComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
