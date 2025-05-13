import Head from "next/head";
import { useEffect, useState } from "react";
import BuildingService from "@services/BuildingService";
import { Building } from "@types";
import Header from "@components/header";
import BuildingOverview from "@components/buildings/BuildingOverview";
import useInterval from 'use-interval';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';

const BuildingsPage = () => {
  const [buildings, setBuildings] = useState<Building[]>([]);
  const [error, setError] = useState<string>();

  const fetchBuildings = async () => {
    setError('');
    try {
      const response = await BuildingService.getAllBuildings();

      if (!response.ok) {
        if (response.status === 401) {
          setError("You are not authorized to view this page. Please login first.");
        } else if (response.status === 403) {
          setError("Access denied. Only admins and lecturers can view this page.");
        } else {
          const data = await response.json();
          setError(data.message || "Failed to load buildings");
        }
      } else {
        const data = await response.json();
        setBuildings(data);
      }
    } catch (err) {
      setError("Unexpected error occurred");
    }
  };

  useEffect(() => {
    fetchBuildings();
  }, []);

  useInterval(() => {
    if (!error) fetchBuildings();
  }, 1000);

  return (
    <>
      <Head>
        <title>Buildings</title>
      </Head>
      <Header />
      <main className="p-6 min-h-screen flex flex-col items-center">
        <h1>Buildings Overview</h1>
        {error ? (
          <div className="text-red-600">{error}</div>
        ) : (
          <BuildingOverview buildings={buildings} />
        )}
      </main>
    </>
  );
};

export const getServerSideProps = async ({ locale }: { locale: string }) => ({
  props: {
    ...(await serverSideTranslations(locale ?? 'en', ['common'])),
  },
});

export default BuildingsPage;
