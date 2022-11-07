#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
 
typedef struct {
    char name[55];
    int team;
    int power;
    bool visibility;
} Player;
 
Player *findPlayerByName(char name[55], Player *players, int m) {
    for (int i = 0; i < m; i++) {
        if (strcmp(name, players[i].name) == 0) {
            return &players[i];
        }
    }
    return NULL;
}
 
int main() {
    int N, M;
    int k_super = 0;
    char team_names[10][55];
 
    Player players[200];
 
    FILE *fp;
 
    FILE *ofp;
 
    fp = fopen("input.txt", "r");
    ofp = fopen("output.txt", "w");
 
    fscanf(fp, "%d\n", &N);
    printf("N : %d\n", N);
 
    if (N < 1 || N > 10) {
        fprintf(ofp, "Invalid inputs\n");
        return 0;
    }
 
    for (int i = 0; i < N; i++) {
        fscanf(fp, "%s\n", team_names[i]);
        printf("Team %d : %s\n", i, team_names[i]);
    }
 
    for (int i = 0; i < N; i++) {
        short L = (short)strlen(team_names[i]);
 
        if (L > 20 || L < 2) {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
        if (!isupper(team_names[i][0])) {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
        for (int j = 1; j < L; j++) {
            if (!isalpha(team_names[i][j])) {
                fprintf(ofp, "Invalid inputs\n");
                return 0;
            }
 
        }
 
    }
 
    fscanf(fp, "%d\n", &M);
    printf("M : %d\n", M);
    if (M < N || M > 100) {
        fprintf(ofp, "Invalid inputs\n");
        return 0;
    }
    char visibility[255] = "";
 
    for (int i = 0; i < M; i++) {
        fscanf(fp, "%s\n", players[i].name);
        fscanf(fp, "%d\n", &players[i].team);
        fscanf(fp, "%d\n", &players[i].power);
        fscanf(fp, "%s\n", visibility);
 
        short L = (short)strlen(players[i].name);
 
        if (L > 20 || L < 2) {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
        if (!isupper(players[i].name[0])) {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
        for (int j = 1; j < L; j++) {
            if (!isalpha(players[i].name[j])) {
                fprintf(ofp, "Invalid inputs\n");
                return 0;
            }
        }
 
        if (players[i].team < 0 || players[i].team > N - 1) {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
        if (players[i].power < 0 || players[i].power > 1000) {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
        if (strcmp(visibility, "True") == 0) {
            players[i].visibility = true;
        } else if (strcmp(visibility, "False") == 0) {
            players[i].visibility = false;
        } else {
            fprintf(ofp, "Invalid inputs\n");
            return 0;
        }
 
 
        printf("name : %s\n", players[i].name);
        printf("team : %d\n", players[i].team);
        printf("power : %d\n", players[i].power);
        printf("visibility : %d\n", players[i].visibility);
 
    }
 
    char action_string[225];
    char name_i[50];
    char name_j[50];
 
    int action_count = 0;
 
    while (!feof(fp) && (action_count++ < 1010)) {
        action_string[0] = '\0';
        fscanf(fp, "%s", action_string);
 
        if (strcmp(action_string, "flip_visibility") == 0) {
            fscanf(fp, "%s\n", name_i);
            printf("%s %s\n", action_string, name_i);
        } else {
            fscanf(fp, "%s %s\n", name_i, name_j);
            printf("%s %s %s\n", action_string, name_i, name_j);
        }
 
        if (action_string[0] == '\0' || action_string[0] == '\n')
            break;
 
        if (strcmp(action_string, "attack") == 0) {
            Player *player_i = findPlayerByName(name_i, players, M);
            Player *player_j = findPlayerByName(name_j, players, M);
 
            if (player_i == NULL || player_j == NULL) {
                fprintf(ofp, "Invalid inputs\n");
                return 0;
            }
 
            if (player_i->visibility == false) {
                fprintf(ofp, "This player can't play\n");
                continue;
            }
 
            if (player_i->power == 0) {
                // warning
                fprintf(ofp, "This player is frozen\n");
                continue;
            }
 
            if (player_j->visibility == 0) {
                player_i->power = 0;
 
            } else if (player_i->power > player_j->power) {
                player_i->power += player_i->power - player_j->power;
                player_j->power = 0;
                if (player_i->power > 1000) {
                    player_i->power = 1000;
                }
            } else if (player_i->power < player_j->power) {
                player_j->power += player_j->power - player_i->power;
                player_i->power = 0;
                if (player_j->power > 1000) {
                    player_j->power = 1000;
                }
            } else {
                player_i->power = 0;
                player_j->power = 0;
            }
 
 
        } else if (strcmp(action_string, "flip_visibility") == 0) {
            Player *player_i = findPlayerByName(name_i, players, M);
 
            if (player_i == NULL) {
                fprintf(ofp, "Invalid inputs\n");
                return 0;
            }
 
            if (player_i->power == 0) {
                // warning
                fprintf(ofp, "This player is frozen\n");
                continue;
            }
 
            player_i->visibility = !(player_i->visibility);
 
        } else if (strcmp(action_string, "heal") == 0) {
            Player *player_i = findPlayerByName(name_i, players, M);
            Player *player_j = findPlayerByName(name_j, players, M);
 
            if (player_i == NULL || player_j == NULL) {
                fprintf(ofp, "Invalid inputs\n");
                return 0;
            }
 
            if (player_i->visibility == false) {
                // warning
                fprintf(ofp, "This player can't play\n");
                continue;
            }
 
            if (player_i->power == 0) {
                // warning
                fprintf(ofp, "This player is frozen\n");
                continue;
            }
 
            if (player_i->team != player_j->team) {
                // warning
                fprintf(ofp, "Both players should be from the same team\n");
                continue;
            }
 
            if (player_i == player_j) {
                // warning
                fprintf(ofp, "The player cannot heal itself\n");
                continue;
            }
 
            int delta = (player_i->power + 1) / 2;
            player_i->power = delta;
            player_j->power += delta;
 
            if (player_j->power > 1000) {
                player_j->power = 1000;
            }
 
        } else if (strcmp(action_string, "super") == 0) {
            Player *player_i = findPlayerByName(name_i, players, M);
            Player *player_j = findPlayerByName(name_j, players, M);
 
            if (player_i == NULL || player_j == NULL) {
                fprintf(ofp, "Invalid inputs\n");
                return 0;
            }
 
            if (player_i->visibility == false) {
                // warning
                fprintf(ofp, "This player can't play\n");
                continue;
            }
 
            if (player_i->power == 0) {
                // warning
                fprintf(ofp, "This player is frozen\n");
                continue;
            }
 
            if (player_i->team != player_j->team) {
                // warning
                fprintf(ofp, "Both players should be from the same team\n");
                continue;
            }
 
            if (player_i == player_j) {
                // warning
                fprintf(ofp, "The player cannot do super action with itself\n");
            }
 
            player_i->power += player_j->power;
 
            if (player_i->power > 1000) {
                player_i->power = 1000;
            }
 
            player_j->power = 0;
            player_i->visibility = true;
            sprintf(player_i->name, "S_%d", k_super++);
        }
    }
 
    if (action_count > 1000) {
        freopen("output.txt", "w", ofp);
        fprintf(ofp, "Invalid inputs\n");
        return 0;
    }
 
    int score[N];
    for (int i = 0; i < N; i++) {
        score[i] = 0;
    }
 
    for (int j = 0; j < M; j++) {
        score[players[j].team] += players[j].power;
    }
 
    int max_score = -1;
    int max_score_team = -1;
    bool tie = false;
 
    for (int i = 0; i < N; i++) {
        if (score[i] > max_score) {
            max_score = score[i];
            max_score_team = i;
            tie = false;
        } else if (score[i] == max_score) {
            tie = true;
        }
    }
 
    if (tie) {
        fprintf(ofp, "It's a tie\n");
    } else {
        fprintf(ofp, "The chosen wizard is %s\n", team_names[max_score_team]);
    }
 
    fclose(fp);
    fclose(ofp);
 
    return 0;
}
