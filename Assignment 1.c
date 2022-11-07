#include <stdio.h>
#include <string.h>
int main() {

    FILE *fp;

    FILE *ofp;

    int x, sum = 0;

    char c[255][255], temp[255];

    char name[] = "input.txt";

    fp = fopen(name, "r");

    ofp = fopen("output.txt", "w");

    fscanf(fp, "%d\n", &x);

    while(!feof(fp))
    {
        if(fscanf(fp, "%s\n", &c[sum]))

            sum++;
    }

    if (x != sum){

        fprintf(ofp, "Error in the input.txt\n");

        return 0;
    }

    if (x > 100){

        fprintf(ofp, "Error in the input.txt\n");

        return 0;
    }

    for (int i = 0; i < sum; i++)
    {
        for (int j = 0; c[i][j] != '\0'; j++)
        {
            if ((c[i][j] < 97 || c[i][j] > 122) && j != 0)
            {
                fprintf(ofp, "Error in the input.txt\n");

                return 0;
            }
            if (c[i][0] < 65 || c[i][0] > 90)
            {
                fprintf(ofp, "Error in the input.txt\n");

                return 0;
            }
        }

    }

    for(int i=0; i<=x; i++)
    {

        fgets(c[i], sizeof c, fp);
    }

    for(int i=2; i<=x; i++)

        for(int j=0; j<=x-i; j++)

            if(strcmp(c[j], c[j+1]) > 0)
            {
                strcpy(temp, c[j]);

                strcpy(c[j], c[j+1]);

                strcpy(c[j+1], temp);
            }

    for(int i=0; i<=x; i++)
        fprintf(ofp, "%s\n", c[i]);


    fclose(fp);

    fclose(ofp);

    return 0;
}
